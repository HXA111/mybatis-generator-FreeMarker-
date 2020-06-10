package dao;

import vo.User;

import java.util.List;

public interface IUserDAO {
    //根据主键查找记录
    public User getByUserName(String userName);
    //根据条件查找
    public List<User> query(User user);
    //增加
    public int insert(User user);
    //修改
    public int update(User user);
    //按主键删除
    public int delete(String userName);
    //按条件删除
    public int deleteCondition(User user);
}
