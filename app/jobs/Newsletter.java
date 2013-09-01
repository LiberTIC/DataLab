package jobs;

import models.NewsLetterMember;
import models.cms.CMSPage;
import notifier.Mails;
import play.jobs.Job;
import play.jobs.On;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Job to send the newsletter : Evey day, it send the blog posts of the previous day
 */
@On("0 0 12 * * ?")
public class Newsletter extends Job {

    @Override
    public void doJob() throws Exception {
        List<CMSPage> posts = CMSPage.find("created > ?1 and template = ?2 order by created asc", today(), "blog").fetch();
        if(posts.size() > 0) {
            List<NewsLetterMember> members = NewsLetterMember.findAll();
            for(NewsLetterMember member : members){
                Mails.newsletterSend(posts, member.email);
            }
        }
    }

    /**
     * Get the previous day, setting to midnight.
     *
     * @return date
     */
    public static Date today() {
        Calendar lastDay = toCalendar(new Date());
        lastDay.add(Calendar.DATE, -1);
        return setToMidnight(lastDay.getTime());
    }

    /**
     * Setting a date to the midnight.
     *
     * @param date
     * @return date
     */
    public static Date setToMidnight(final Date date) {
        return org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
    }

    /**
     * Convert a <code>Date</code> to a <code>Calendar</code>.
     *
     * @param date
     * @return
     */
    public static Calendar toCalendar(final Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal;
    }

}
