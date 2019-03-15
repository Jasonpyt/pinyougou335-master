package cn.itcast.core.service.address;

import cn.itcast.core.pojo.address.Address;

import java.util.List;

/**
 * @author wophy
 */
public interface AddressService{

    List<Address> findAddressListByLoginUser(String username);
}
