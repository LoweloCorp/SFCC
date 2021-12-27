package com.lowelostudents.caloriecounter;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.lowelostudents.caloriecounter.data.AppDatabase;
import com.lowelostudents.caloriecounter.models.Day;
import com.lowelostudents.caloriecounter.models.DayDao;
import com.lowelostudents.caloriecounter.models.Day_Food_Relation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class IntegrationTests {
    AppDatabase appdb = AppDatabase.getInMemoryInstance(InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext());

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.lowelostudents.caloriecounter", appContext.getPackageName());
    }

    @Test
    public void createAndReadDay(){
        DayDao dayDao = appdb.dayDao();
        Day day = new Day();
        dayDao.insertAll(day);
        List<Day_Food_Relation> dfr = dayDao.getDays();
        Calendar cal = Calendar.getInstance();
        assertEquals(cal.get(Calendar.DATE), dfr.get(0).day.getDayId());
        assertEquals(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()), dfr.get(0).day.getName());
        assertEquals(day.getDayId() , dayDao.getDay(cal.get(Calendar.DATE)).day.getDayId());
    }
}