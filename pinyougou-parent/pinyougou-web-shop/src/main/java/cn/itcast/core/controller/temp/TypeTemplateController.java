package cn.itcast.core.controller.temp;

import cn.itcast.core.pojo.template.TypeTemplate;

import cn.itcast.core.service.typeTemplate.TypeTemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

    @Reference
    private TypeTemplateService typeTemplateService;

    /**
     * 新增商品：回显品牌以及扩展属性
     * @param id
     * @return
     */
    @RequestMapping("/findOne.do")
    public TypeTemplate findOne(Long id){

        return typeTemplateService.findOne(id);
    }

    /**
     * 根据模板的 id 显示规格
     * [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"},{"id":28,"text":"手机屏幕尺寸"}]
     */
    @RequestMapping("/findBySpecList.do")
    public List<Map> findBySpecList(Long id){
        return typeTemplateService.findBySpecList(id);
    }
}
