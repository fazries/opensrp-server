package org.opensrp.register.mcare.report.mis1.birthAndDeath;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.report.mis1.ReportCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BirthCountCalculator extends ReportCalculator {

    private long totalCountOfLiveBirth ;


    public BirthCountCalculator(long startDateTime, long endDateTime) {
        super(startDateTime, endDateTime);
        this.initCountVariables();

    }

    public long getTotalCountOfLiveBirth() {

        return totalCountOfLiveBirth;
    }

    public void initCountVariables() {

        this.totalCountOfLiveBirth = 0;
    }

    @Override
    public void calculate(Members member) {

        this.totalCountOfLiveBirth += addTotalLiveBirthChild(member);
    }

    private long addTotalLiveBirthChild(Members member) {
        System.out.println("total live child");
        Date dooDate = null;
        Date startDate = null;
        Date endDate = null;
        long value = 0;
        if( member.getMember_Birth_Date() != null ){
            String deliveryDateStr =  member.getMember_Birth_Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dooDate = simpleDateFormat.parse(deliveryDateStr);
                startDate = new Date( startDateTime * 1000);
                endDate = new Date( endDateTime * 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(dooDate.after(startDate) && dooDate.before(endDate) || dooDate.equals(startDate)){

                if(member.getMember_Birth_Date() != null){
                   value = 1;
                }
                else{
                    value = 0;
                }

            }

        }

        return value;
    }

}