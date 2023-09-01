package jpabook.jpashop

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringBootJpaPractice2Application

fun main(args: Array<String>) {
    runApplication<SpringBootJpaPractice2Application>(*args)
}

@Bean
fun hibernate5Module(): Hibernate5JakartaModule {
    val hibernate5Module = Hibernate5JakartaModule()

    //강제 지연 로딩 설정
    //hibernate5Module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true)

    return hibernate5Module
}
