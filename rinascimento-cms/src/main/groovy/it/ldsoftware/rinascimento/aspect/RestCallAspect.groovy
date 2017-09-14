package it.ldsoftware.rinascimento.aspect

import it.ldsoftware.rinascimento.exception.InstallationException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

/**
 * This aspect defines actions that wrap all exceptions in rest-thrown exceptions in order to correctly
 * manage them with the rest controller advice
 *
 * @author Luca Di Stefano
 */
@Aspect
class RestCallAspect {

    @Around("execution(*it.ldsoftware.rinascimento.service.InstallationService.*(..))")
    void wrapExceptions(ProceedingJoinPoint joinpoint) {
        try {
            joinpoint.proceed()
        } catch (Exception e) {
            throw new InstallationException(e)
        }
    }


}
