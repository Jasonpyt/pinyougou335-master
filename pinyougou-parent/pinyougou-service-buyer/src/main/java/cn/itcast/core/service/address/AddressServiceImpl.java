package cn.itcast.core.service.address;

import cn.itcast.core.dao.address.AddressDao;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.AddressQuery;

import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressDao addressDao;

    @Override
    public List<Address> findAddressListByLoginUser(String username) {
        AddressQuery query = new AddressQuery();
        query.createCriteria().andUserIdEqualTo(username);
        List<Address> addressList = addressDao.selectByExample(query);
        return addressList;
    }
}
