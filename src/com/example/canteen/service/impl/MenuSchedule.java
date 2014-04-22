package com.example.canteen.service.impl;

import com.example.canteen.service.TimeService;
import com.example.canteen.service.data.Menu;
import com.example.canteen.service.data.PublishingDetails;

import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class MenuSchedule {
    private final TimeService _timeService;
    private PriorityQueue<Menu> _queue = new PriorityQueue<Menu>(1, new Comparator<Menu>() {
        @Override
        public int compare(Menu menu, Menu menu2) {
            return menu.orderingDeadline().compareTo(menu2.orderingDeadline());
        }
    });
    private PublishingDetails _currentMenu = new PublishingDetails(null, false, false);
    private Date _scheduleCheckDate;
    private Runnable _checkSchedule = new Runnable() {
        @Override
        public void run() {
            checkSchedule();
        }
    };
    private Runnable _onMenuUpdate;

    public MenuSchedule(TimeService timeService) {
        _timeService = timeService;
    }

    public Menu schedule(final Menu menu) {
        _queue.add(menu);
        checkSchedule();
        return menu;
    }

    public void remove(Menu menu) {
        _queue.remove(menu);
        checkSchedule();
    }

    public PublishingDetails currentMenu() {
        return _currentMenu;
    }

    public PublishingDetails nextMenuForDate(Date date) {
        Menu nextMenu = null;
        for (Menu menu : _queue) {
            if (menu.validThrough().after(date)) {
                nextMenu = menu;
                break;
            }
        }

        PublishingDetails details = null;
        if (null != nextMenu) {
            boolean isAvailable = date.after(nextMenu.publishingDate());
            boolean orderingEnabled = isAvailable && date.before(nextMenu.orderingDeadline());
            details = new PublishingDetails(nextMenu, isAvailable, orderingEnabled);

        } else {
            details = new PublishingDetails(null, false, false);
        }
        return details;
    }

    private void checkSchedule() {
        Date now = _timeService.now();
        PublishingDetails menuDetails = nextMenuForDate(now);
        Date nextCheckDate = null;
        if (null != menuDetails.menu) {
            if (!menuDetails.isAvailable) {
                nextCheckDate = menuDetails.menu.publishingDate();
            } else if (menuDetails.orderingEnabled) {
                nextCheckDate = menuDetails.menu.orderingDeadline();
            } else {
                nextCheckDate = menuDetails.menu.validThrough();
            }
        }
        if ((nextCheckDate == null || _scheduleCheckDate == null) && _scheduleCheckDate != nextCheckDate || !_scheduleCheckDate.equals(nextCheckDate)) {
            _timeService.cancel(_checkSchedule);
            _scheduleCheckDate = nextCheckDate;
            if (null != _scheduleCheckDate) {
                _timeService.schedule(_scheduleCheckDate, _checkSchedule);
            }
        }

        if (!_currentMenu.equals(menuDetails)) {
            _currentMenu = menuDetails;
            notifyMenuUpdated();
        }
    }

    public void setOnMenuUpdate(Runnable action) {
        _onMenuUpdate = action;
    }

    public void notifyMenuUpdated() {
        if (null != _onMenuUpdate) {
            _onMenuUpdate.run();
        }
    }
}
