package com.hanghae.bulletbox.member.controller;

import com.hanghae.bulletbox.common.response.Response;
import com.hanghae.bulletbox.member.dto.MemberDto;
import com.hanghae.bulletbox.member.dto.RequestLoginDto;
import com.hanghae.bulletbox.member.dto.RequestSignupDto;
import com.hanghae.bulletbox.member.dto.VerifyCodeDto;
import com.hanghae.bulletbox.member.service.MailService;
import com.hanghae.bulletbox.member.service.MemberService;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.hanghae.bulletbox.common.exception.ExceptionMessage.DIFFERENT_CODE_MSG;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MailService mailService;


    @PostMapping("/signup")
    public Response<?> signup(@Validated @RequestBody RequestSignupDto requestSignupDto){

        MemberDto memberDto = MemberDto.toMemberDto(requestSignupDto);
        memberService.signup(memberDto);

        return Response.success(201, "회원가입이 완료되었습니다.", null);
    }
    @PostMapping("/signup/email-validate")
    public Response<?> mailConfirm(HttpSession httpSession, @RequestBody MemberDto memberDto) throws Exception{
        String email = memberDto.getEmail();
        String code = mailService.sendSimpleMessage(email, memberDto);
        httpSession.setAttribute("code", code);
        return Response.success(200, "이메일 인증 메일이 전송되었습니다.", null);
    }

    @PostMapping("/signup/verifycode")
    public Response<?> verifyCode(HttpSession httpSession, @RequestBody VerifyCodeDto verifyCodeDto){
        if((verifyCodeDto.getVerifyCode()).equals(httpSession.getAttribute("code"))){
        }else{
            throw new IllegalStateException(DIFFERENT_CODE_MSG.getMsg());
        }
        return Response.success(200,"이메일 인증이 완료되었습니다.", true);
    }

    @PostMapping("/login")
    public Response<?> login(@RequestBody RequestLoginDto requestLoginDto, HttpServletResponse httpServletResponse){

        MemberDto memberDto = MemberDto.toMemberDto(requestLoginDto);
        memberService.login(memberDto, httpServletResponse);

        return Response.success(200, "로그인이 완료되었습니다.", null);
    }
}

