package in.auth.repository;

import org.springframework.stereotype.Repository;

import in.auth.base.AbstractJpaDao;
import in.auth.entity.LoginUser;

@Repository
public class LoginUserRepository extends AbstractJpaDao<LoginUser, String> {

}
