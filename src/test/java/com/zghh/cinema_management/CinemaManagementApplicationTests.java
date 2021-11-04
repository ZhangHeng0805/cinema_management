package com.zghh.cinema_management;

import com.zghh.cinema_management.bean.Administrator;
import com.zghh.cinema_management.repository.AdministratorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CinemaManagementApplicationTests {
@Autowired
private AdministratorRepository administratorRepository;
    @Test
    void contextLoads() {

    }

}
