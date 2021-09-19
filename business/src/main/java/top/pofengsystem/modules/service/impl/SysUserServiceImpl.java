package top.pofengsystem.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.pofengsystem.modules.entity.SysUser;
import top.pofengsystem.modules.mapper.SysUserMapper;
import top.pofengsystem.modules.service.SysUserService;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
