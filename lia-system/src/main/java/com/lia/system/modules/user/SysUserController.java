package com.lia.system.modules.user;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysDictData;
import com.lia.system.entity.SysFile;
import com.lia.system.entity.SysUser;
import com.lia.system.result.exception.HttpException;
import com.lia.system.security.LoginUser;
import com.lia.system.modules.file.SysFileService;
import com.lia.system.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Value("${token.header}")
    private String header;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysFileService sysFileService;


    /**
     * 获取当前登录用户的信息
     */
    @GetMapping("/getInfo")
    public SysUser getInfo() {
        return sysUserService.getUserDetail(LoginUser.getLoginUserId());
    }


    /**
     * 根据用户id查询用户详细信息
     */
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('system:user:detail')")
    public SysUser getUserDetail(@RequestBody Long userId){
        return sysUserService.getUserDetail(userId);
    }

    /**
     * 获取用户头像
     * @return
     */
    @GetMapping("/getHeadImg")
    public Long getHeadImg() {
        SysUser user = this.getInfo();
        if (user.getHeadImg() == null) {
            return null;
        }
        SysFile file = new SysFile();
        file.setFileId(user.getHeadImg());
        List<SysFile> sysFile = sysFileService.getSysFile(file);
        if (sysFile != null && sysFile.size() > 0) {
            return sysFile.get(0).getFileId();
        } else {
            return null;
        }
    }


    /**
     * 更新用户头像
     * @param file
     * @return
     */
    @PostMapping("/updateHeadImg")
    public SysFile updateHeadImg(MultipartFile file) {
        return sysUserService.updateHeadImg(file);
    }


    /**
     * 用户登录，登录成功后返回加密的token字符串
     * 之后的请求携带token在header中校验身份
     * @param user 用户信息
     * @return token字符串
     */
    @PostMapping("/login")
    public String sysUserLogin(@RequestBody SysUser user) {
        return sysUserService.getAuthorization(user);
    }


    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public void logout() {
        sysUserService.logout(LoginUser.getLoginUserId());
    }


    /**
     * 分页查询用户列表
     * @param user    查询参数
     * @param current 当前页码
     * @param size    每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:user:getPage')")
    public PageInfo<SysUser> getSysUserPage(@RequestBody SysUser user, Integer current, Integer size) {
        if (current != null && size != null) {
            PageHelper.startPage(current, size);
        }
        return new PageInfo<>(sysUserService.findSysUser(user));
    }


    /**
     * 用户注册
     * @param user 用户参数
     * @param registerCode 注册码
     * @return 相应信息
     */
    @PostMapping("/register")
    public int registerUser(@RequestBody SysUser user, String registerCode){
        return sysUserService.register(user, registerCode);
    }


    /**
     * 新增和编辑用户
     * @param user 用户数据，每条数据如果有userId则为修改，userId为null则为新增
     * @return 操作成功的数量
     */
    @PostMapping("/saveUser")
    @PreAuthorize("hasAuthority('system:user:saveUser')")
    public int saveUser(@RequestBody SysUser user) {
        if(user.getUserId() == null){
            return sysUserService.register(user, null);
        }else{
            return sysUserService.editUser(user);
        }
    }


    /**
     * 批量删除用户
     * @param userIds 用户的id列表
     * @return 删除成功的数量
     */
    @PostMapping("/deleteUsers")
    @PreAuthorize("hasAuthority('system:user:deleteUsers')")
    public int deleteUsers(@RequestBody List<Long> userIds) {
        return sysUserService.deleteUsers(userIds);
    }


    /**
     * 获取创建人字典表
     */
    @GetMapping("/getCreaterDict")
    @PreAuthorize("hasAuthority('system:user:getCreaterDict')")
    public List<SysDictData> createrDict() {
        return sysUserService.getCreaterDict();
    }


    /**
     * 导出excel
     */
    @PostMapping("/excel")
    @PreAuthorize("hasAuthority('system:user:excel')")
    public void excel(HttpServletResponse response, @RequestBody SysUser user){
        sysUserService.excel(response, user);
    }

}
