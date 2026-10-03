package com.example.demo.aspect;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

	/** データベース関連の例外処理 */
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		
		// 空文字をセット
		model.addAttribute("error", "");
		// メッセージをModelに登録
		model.addAttribute("message", "DataAccessExceptionが発生しました");
		// HTTPのエラーコード
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
	
	/** その他の例外処理 */
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {
		
		log.error("予期しない例外が発生しました", e);
		
		// 空文字をセット
		model.addAttribute("error", "");
		// メッセージをModelに登録
		model.addAttribute("message", "Exceptionが発生しました");
		// HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String noResourceFoundExceptionHandler(
	        NoResourceFoundException e,
	        Model model) {

	    model.addAttribute("error", "Not Found");
	    model.addAttribute("message", "ページが見つかりません");
	    model.addAttribute("status", HttpStatus.NOT_FOUND);

	    return "error";
	}
}
