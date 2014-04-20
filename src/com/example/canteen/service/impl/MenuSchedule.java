package com.example.canteen.service.impl;

import com.example.canteen.service.TimeService;
import com.example.canteen.service.data.Menu;

import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class MenuSchedule {
    private final TimeService _timeService;
    private Menu _currentMenu;
    private Menu _nextMenu;
    private PriorityQueue<Menu> _queue = new PriorityQueue<Menu>(1, new Comparator<Menu>() {
        @Override
        public int compare(Menu menu, Menu menu2) {
            return menu.orderingDeadline().compareTo(menu2.orderingDeadline());
        }
    });
    private Date _scheduleCheckDate;
    private Runnable _checkSchedule = new Runnable() {
        @Override
        public void run() {
            checkSchedule();
        }
    };

    public MenuSchedule(TimeService timeService) {
        _timeService = timeService;
    }

    public void schedule(final Menu menu) {
        _queue.add(menu);
        checkSchedule();
    }

    public void remove(Menu menu) {
        _queue.remove(menu);
        checkSchedule();
    }

    public Menu nextMenu() {
        Date now = _timeService.now();
        Menu nextMenu = null;
        for (Menu menu : _queue) {
            if (menu.validThrough().after(now)) {
                nextMenu = menu;
                break;
            }
        }
        return nextMenu;
    }

    public Menu currentMenu() {
        Menu nextMenu = nextMenu();
        if (nextMenu.publishingDate().before(_timeService.now())) {
            return nextMenu;
        }
        return null;
    }

    private void checkSchedule() {
        // todo: detect changes
        Menu nextMenu = nextMenu();
        Date nextCheckDate = null;
        if (null != nextMenu) {
            Date now = _timeService.now();
            nextCheckDate = nextMenu.publishingDate();
            if (nextCheckDate.before(now)) {
                nextCheckDate = nextMenu.orderingDeadline();
            }
            if (nextCheckDate.before(now)) {
                nextCheckDate = nextMenu.validThrough();
            }
        }
        if (_scheduleCheckDate != nextCheckDate || !_scheduleCheckDate.equals(nextCheckDate)) {
            _timeService.cancel(_checkSchedule);
            _scheduleCheckDate = nextCheckDate;
            if (null != _scheduleCheckDate) {
                _timeService.schedule(_scheduleCheckDate, _checkSchedule);
            }
        }
    }


}
