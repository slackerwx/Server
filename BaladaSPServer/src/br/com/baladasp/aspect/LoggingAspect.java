package br.com.baladasp.aspect;

import java.util.ArrayList;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import br.com.baladasp.cdp.usuario.StatusUsuario;

@Aspect
public class LoggingAspect {
	
	
	@Before("execution(* br.com.baladasp.cgt.bo.StatusUsuariosBO.*(..))")
	public void logBefore(JoinPoint joinPoint){
		//System.out.println("***********Página " + pageNum + " ************************");
		System.out.println("**************************");
		System.out.println("ListStatus: " + joinPoint.getSignature().getName());
		System.out.println("**************************");
	}
	
	 @AfterReturning(
		      pointcut = "execution(* br.com.baladasp.cgt.bo.StatusUsuariosBO.*(..))",
		      returning= "result")
		   public void logAfterReturning(JoinPoint joinPoint, Object result) {
		 
			System.out.println("logAfterReturning() is running!");
			System.out.println("hijacked : " + joinPoint.getSignature().getName());
			System.out.println("Resultado : ");
			imprimirListStatus((ArrayList<StatusUsuario>) result);
		 
		   }
	 
	 private void imprimirListStatus(ArrayList<StatusUsuario> statusUsuarios) {
		
			for (StatusUsuario statusUsuario : statusUsuarios) {
				System.out.println(statusUsuario.getId());
			}
		}
}
