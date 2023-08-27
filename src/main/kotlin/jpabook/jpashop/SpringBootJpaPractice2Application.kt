package jpabook.jpashop

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringBootJpaPractice2Application

fun main(args: Array<String>) {
    runApplication<SpringBootJpaPractice2Application>(*args)
}

@Bean
fun hibernate5Module(): Hibernate5Module {
    val hibernate5Module = Hibernate5Module()

    //강제 지연 로딩 설정
    hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true)

    return hibernate5Module
}
