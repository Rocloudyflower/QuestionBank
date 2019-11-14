package com.example.question_bank.service;

import com.example.question_bank.dao.PropertyValueDAO;
import com.example.question_bank.pojo.Property;
import com.example.question_bank.pojo.PropertyValue;
import com.example.question_bank.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueService {
    @Autowired
    PropertyValueDAO propertyValueDAO;
    @Autowired PropertyService propertyService;
    public void update(PropertyValue bean) {
        propertyValueDAO.save(bean);
    }

    public void init(Question question) {
        List<Property> propertys= propertyService.listByCategory(question.getCategory());
        for (Property property: propertys) {
            PropertyValue propertyValue = getByPropertyAndProduct(question, property);
            if(null == propertyValue){
                propertyValue = new PropertyValue();
                propertyValue.setQuestion(question);
                propertyValue.setProperty(property);
                propertyValueDAO.save(propertyValue);
            }
        }
    }

    public PropertyValue getByPropertyAndProduct(Question question, Property property) {
        return propertyValueDAO.getByPropertyAndQuestion(property,question);
    }

    public List<PropertyValue> list(Question question) {
        return propertyValueDAO.findByQuestionOrderByIdAsc(question);
    }

    public void delete(Question question){
        propertyValueDAO.deleteByQuestion(question);
    }


}
