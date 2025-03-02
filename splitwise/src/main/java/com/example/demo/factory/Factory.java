package com.example.demo.factory;

import com.example.demo.enums.SplitTypeEnum;
import com.example.demo.strategy.EqualSplit;
import com.example.demo.strategy.ExactSplit;
import com.example.demo.strategy.SplitType;
import org.springframework.stereotype.Component;

@Component
public class Factory {

    public SplitType getSplitType(SplitTypeEnum splitType) {
        if (splitType == null) {
            return null;
        }
        if ( splitType == SplitTypeEnum.EQUAL) {
            return new EqualSplit();
        } else if (splitType == SplitTypeEnum.EXACT) {
             return new ExactSplit();
        }
        return null;
    }

}
