package sarapavo.la_bottega_del_viso.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
}
