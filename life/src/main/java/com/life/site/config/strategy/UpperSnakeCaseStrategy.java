package com.life.site.config.strategy;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class UpperSnakeCaseStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {

    public static final PropertyNamingStrategy UPPER_SNAKE_CASE = new UpperSnakeCaseStrategy();

    @Override public String translate(String input) {
        if (input == null) return input; // garbage in, garbage out
         int length = input.length();
         StringBuilder result = new StringBuilder(length * 2);
         int resultLength = 0;
         boolean wasPrevTranslated = false;
         for (int i = 0; i < length; i++)
         {
             char c = input.charAt(i);
             if (i > 0 || c != '_') // skip first starting underscore
                  { if (Character.isUpperCase(c))
                    { if (!wasPrevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != '_')
                    { result.append('_'); resultLength++; }
                        wasPrevTranslated = true; }
                  else { wasPrevTranslated = false;
                  }
                  result.append(c); resultLength++; }
         }
         return resultLength > 0 ? result.toString().toUpperCase() : input;
          }
    }
