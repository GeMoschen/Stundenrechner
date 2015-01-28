package de.gemo.stunden.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.gemo.stunden.MainActivity;
import de.gemo.stunden.fragments.DatePickerFragment;
import de.gemo.stunden.fragments.DeleteDayFragment;
import de.gemo.stunden.fragments.TimePickerFragment;
import de.gemo.stunden.units.Day;
import de.gemo.stunden.units.DayHolder;
import de.gemo.stunden.units.Month;
import de.gemo.stunden.units.OnSwipeTouchListener;
import de.gemo.stunden.units.undostates.DayCreateState;
import de.gemo.stunden.units.undostates.DayEditState;
import de.gemo.stunden.units.undostates.MonthState;
import de.gemo.stunden.units.undostates.UndoManager;

public class GUIUtils {

    public static final int FOREGROUND = Color.rgb(255, 255, 50);
    public static final int BACKGROUND = Color.rgb(10, 10, 10);
    public static final int DIVIDER = Color.rgb(255, 255, 200);

    public static MainActivity APP;
    private static ViewGroup MAIN_VIEW;
    private static Menu MENU;

    public static void setApp(MainActivity app) {
        GUIUtils.APP = app;
    }

    public static void setViewGroup(ViewGroup viewGroup) {
        GUIUtils.MAIN_VIEW = viewGroup;
    }

    public static void setMenu(Menu menu) {
        GUIUtils.MENU = menu;
    }

    private static void refreshTitle() {
        APP.setTitle(APP.getDayHolder().getActiveMonth().getStartDate() + " - " + APP.getDayHolder().getActiveMonth().getEndDate() + " - Stunden: " + APP.getDayHolder().getActiveMonth().getWorkedMinutesAsString());
        ColorDrawable colorDrawable = new ColorDrawable(Color.RED);
        if (APP.getActionBar() != null) {
            APP.getActionBar().setBackgroundDrawable(colorDrawable);
            APP.invalidateOptionsMenu();
        }
    }

    private static void refreshDayList() {
        // add days
        for (Day day : APP.getDayHolder().getActiveMonth().getDays()) {
            addSingleDay(day);
        }

        // ADD SPACER
        addSpacer(MAIN_VIEW, BACKGROUND, 200);
    }

    private static View addSpacer(ViewGroup parent, int color, int height) {
        // add divider
        View divider = new View(APP);
        divider.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
        divider.setBackgroundColor(color);
        parent.addView(divider);
        return divider;
    }

    private static View addSpacer(ViewGroup parent, int height) {
        return addSpacer(parent, BACKGROUND, 20);
    }

    private static View addDivider(ViewGroup parent) {
        return addSpacer(parent, DIVIDER, 1);
    }

    private static void addSingleDay(final Day day) {
        // create vertical layout
        LinearLayout verticalLayout = new LinearLayout(APP.getApplicationContext());
        verticalLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 120));
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        MAIN_VIEW.addView(verticalLayout);

        // add labeled text
        String topic = day.getDateString() + " - Pause: " + day.getPauseString() + " - Stunden: " + day.getWorkString();
        String data = day.getStartString() + " bis " + day.getEndString();

        final View view = addLabeledText(verticalLayout, topic, data).get(0);
        view.setOnTouchListener(new OnSwipeTouchListener() {
            @Override
            public boolean onSwipeLeft() {
                UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                APP.getDayHolder().nextMonth();
                showMonthView();
                return true;
            }

            @Override
            public boolean onSwipeRight() {
                UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                APP.getDayHolder().previousMonth();
                showMonthView();
                return true;
            }

            @Override
            public boolean onDoubleTouch() {
                UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                showDayView();
                return true;
            }

            @Override
            public boolean onTouch() {
                UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                showDayView(day);
                return true;
            }

            @Override
            public void onLongTouch() {
                DialogFragment newFragment = new DeleteDayFragment(APP, day);
                newFragment.show(APP.getSupportFragmentManager(), "dialog");
            }
        });

        // add divider
        View divider = addDivider(MAIN_VIEW);
        divider.setOnTouchListener(new OnSwipeTouchListener() {
            @Override
            public boolean onSwipeLeft() {
                UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                APP.getDayHolder().nextMonth();
                showMonthView();
                return true;
            }

            @Override
            public boolean onSwipeRight() {
                UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                APP.getDayHolder().previousMonth();
                showMonthView();
                return true;
            }
        });
    }

    private static List<View> addLabeledText(ViewGroup parent, String topic, String data) {
        // create linear layout
        LinearLayout linearLayout = new LinearLayout(APP.getApplicationContext());
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(BACKGROUND);
        parent.addView(linearLayout);

        // Add first row
        TextView firstRow = new TextView(APP);
        firstRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        firstRow.setText(topic);
        firstRow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        firstRow.setPadding(5, 10, 5, 5);
        firstRow.setTextColor(FOREGROUND);
        linearLayout.addView(firstRow);

        // Add second row
        TextView secondRow = new TextView(APP);
        secondRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        secondRow.setText(data);
        secondRow.setPadding(5, 5, 5, 10);
        secondRow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        secondRow.setTextColor(FOREGROUND);
        linearLayout.addView(secondRow);

        // create result
        List<View> list = new ArrayList<View>();
        list.add(linearLayout);
        list.add(firstRow);
        list.add(secondRow);
        return list;
    }

    public static void showMonthView(Month month) {
        APP.getDayHolder().selectMonth(DayHolder.START_DAY + "." + month.getMonth() + "." + month.getYear());
        showMonthView();
    }

    public static void showMonthView() {
        // clear onTouch-Listener
        MAIN_VIEW.setOnTouchListener(null);

        new CountDownTimer(200, 20) {
            private boolean reduceAlpha = true;
            private int tick = 0;

            @SuppressWarnings("unused")
            private List<View> dateViews, startViews, endViews;

            @Override
            public void onTick(long millisUntilFinished) {
                if (reduceAlpha) {
                    tick++;
                    MAIN_VIEW.setAlpha(MAIN_VIEW.getAlpha() - 0.2f);
                    if (tick == 5) {
                        reduceAlpha = false;

                        // clear view
                        MAIN_VIEW.removeAllViews();

                        refreshTitle();
                        refreshDayList();
                    }
                } else {
                    MAIN_VIEW.setAlpha(MAIN_VIEW.getAlpha() + 0.2f);
                }
            }

            @Override
            public void onFinish() {
                MAIN_VIEW.setAlpha(1.0f);

                // create onTouch-Listener
                MAIN_VIEW.setOnTouchListener(new OnSwipeTouchListener() {
                    @Override
                    public boolean onSwipeLeft() {
                        UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                        APP.getDayHolder().nextMonth();
                        showMonthView();
                        return true;
                    }

                    @Override
                    public boolean onSwipeRight() {
                        UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                        APP.getDayHolder().previousMonth();
                        showMonthView();
                        return true;
                    }

                    @Override
                    public boolean onDoubleTouch() {
                        UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                        showDayView();
                        return true;
                    }
                });

                // create menu
                MENU.clear();

                // ADD
                MENU.add("Neuer Tag").setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                        showDayView();
                        return true;
                    }
                });

                // NEXT MONTH
                MENU.add("Nächster Monat").setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                        APP.getDayHolder().nextMonth();
                        showMonthView();
                        return true;
                    }
                });

                // PREVIOUS MONTH
                MENU.add("Letzter Monat").setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        UndoManager.append(new MonthState(APP.getDayHolder().getActiveMonth()));
                        APP.getDayHolder().previousMonth();
                        showMonthView();
                        return true;
                    }
                });
            }
        }.start();
    }

    public static void showDayView() {
        showDayView(DateUtils.getCurrentDateString(), "09:45", "19:15");
    }

    public static void showDayView(final Day day) {
        showDayView(day, null, null, null);
    }

    public static void showDayView(String dateString, String startTime, String endTime) {
        showDayView(null, dateString, startTime, endTime);
    }

    private static void showDayView(final Day day, final String dateString, final String startTime, final String endTime) {
        // clear listener
        MAIN_VIEW.setOnTouchListener(null);

        new CountDownTimer(200, 20) {

            private boolean reduceAlpha = true;
            private int tick = 0;
            private List<View> dateViews, startViews, endViews;

            @Override
            public void onTick(long millisUntilFinished) {
                if (reduceAlpha) {
                    tick++;
                    MAIN_VIEW.setAlpha(MAIN_VIEW.getAlpha() - 0.2f);
                    if (tick == 5) {
                        reduceAlpha = false;

                        // clear view
                        MAIN_VIEW.removeAllViews();

                        // set title
                        if (day == null) {
                            APP.setTitle("Hinzufügen");
                        } else {
                            APP.setTitle("Bearbeiten");
                        }

                        // clear view
                        MAIN_VIEW.removeAllViews();

                        // add texts
                        if (day == null) {
                            dateViews = addLabeledText(MAIN_VIEW, "Datum:", dateString);
                            addDivider(MAIN_VIEW);
                            startViews = addLabeledText(MAIN_VIEW, "Start:", startTime);
                            addDivider(MAIN_VIEW);
                            endViews = addLabeledText(MAIN_VIEW, "Ende:", endTime);
                            addDivider(MAIN_VIEW);
                        } else {
                            dateViews = addLabeledText(MAIN_VIEW, "Datum:", day.getDateString());
                            addDivider(MAIN_VIEW);
                            startViews = addLabeledText(MAIN_VIEW, "Start:", day.getStartString());
                            addDivider(MAIN_VIEW);
                            endViews = addLabeledText(MAIN_VIEW, "Ende:", day.getEndString());
                            addDivider(MAIN_VIEW);
                        }

                        if (day == null) {
                            // add SPACER
                            addSpacer(MAIN_VIEW, 20);

                            // add SAVE
                            Button btn_save = new Button(APP.getApplicationContext());
                            btn_save.setText("Speichern");
                            btn_save.setTextColor(BACKGROUND);
                            btn_save.setBackgroundColor(FOREGROUND);
                            btn_save.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Day day = new Day(DayHolder.getIDAndIncrement(), DateUtils.getDate(((TextView) dateViews.get(2)).getText().toString()).getTime());
                                    day.setStartTime(StringUtils.getHourFromString(((TextView) startViews.get(2)).getText().toString()), StringUtils.getMinutesFromString(((TextView) startViews.get(2)).getText().toString()));
                                    day.setEndTime(StringUtils.getHourFromString(((TextView) endViews.get(2)).getText().toString()), StringUtils.getMinutesFromString(((TextView) endViews.get(2)).getText().toString()));
                                    day.update();

                                    APP.getDayHolder().addDayToList(day);
                                    APP.getDayHolder().save();

                                    UndoManager.append(new DayCreateState(((TextView) dateViews.get(2)).getText().toString(), ((TextView) startViews.get(2)).getText().toString(), ((TextView) endViews.get(2)).getText().toString()));
                                    APP.getDayHolder().selectMonth(((TextView) dateViews.get(2)).getText().toString());
                                    showMonthView();
                                }
                            });
                            MAIN_VIEW.addView(btn_save);
                        } else {
                            // add SPACER
                            addSpacer(MAIN_VIEW, 20);

                            // add OK
                            Button btn_save = new Button(APP.getApplicationContext());
                            btn_save.setText("Speichern");
                            btn_save.setTextColor(BACKGROUND);
                            btn_save.setBackgroundColor(FOREGROUND);
                            btn_save.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // update day
                                    day.setDate(DateUtils.getDate(((TextView) dateViews.get(2)).getText().toString()).getTime());
                                    day.setStartTime(StringUtils.getHourFromString(((TextView) startViews.get(2)).getText().toString()), StringUtils.getMinutesFromString(((TextView) startViews.get(2)).getText().toString()));
                                    day.setEndTime(StringUtils.getHourFromString(((TextView) endViews.get(2)).getText().toString()), StringUtils.getMinutesFromString(((TextView) endViews.get(2)).getText().toString()));
                                    day.update();

                                    // save changes
                                    APP.getDayHolder().save();

                                    // update view
                                    UndoManager.append(new DayEditState(day));
                                    APP.getDayHolder().selectMonth(((TextView) dateViews.get(2)).getText().toString());
                                    showMonthView();
                                }
                            });
                            MAIN_VIEW.addView(btn_save);
                        }

                        // ///////////////////////////////////////
                        // CREATE MENU
                        // ///////////////////////////////////////

                        // clear menu
                        MENU.clear();

                        // ADD "BACK"
                        MENU.add("Zurück").setOnMenuItemClickListener(new OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (day == null) {
                                    UndoManager.append(new DayCreateState(((TextView) dateViews.get(2)).getText().toString(), ((TextView) startViews.get(2)).getText().toString(), ((TextView) endViews.get(2)).getText().toString()));
                                } else {
                                    UndoManager.append(new DayEditState(day));
                                }
                                showMonthView();
                                return true;
                            }
                        });

                    }
                } else {
                    MAIN_VIEW.setAlpha(MAIN_VIEW.getAlpha() + 0.2f);
                }
            }

            @Override
            public void onFinish() {
                MAIN_VIEW.setAlpha(1.0f);

                // ///////////////////////////////////////
                // CREATE LISTENERS
                // ///////////////////////////////////////

                // DATE
                dateViews.get(0).setOnTouchListener(new OnSwipeTouchListener() {
                    @Override
                    public boolean onTouch() {
                        DialogFragment fragment = new DatePickerFragment((TextView) dateViews.get(2));
                        fragment.show(APP.getSupportFragmentManager(), "datePicker");
                        return true;
                    }

                    @Override
                    public boolean onDoubleTouch() {
                        ((TextView) dateViews.get(2)).setText(DateUtils.getCurrentDateString());
                        return true;
                    }

                    @Override
                    public void onLongTouch() {
                        ((TextView) dateViews.get(2)).setText(DateUtils.getCurrentDateString());
                    }
                });

                // START-TIME
                startViews.get(0).setOnTouchListener(new OnSwipeTouchListener() {
                    @Override
                    public boolean onTouch() {
                        DialogFragment fragment = new TimePickerFragment((TextView) startViews.get(2));
                        fragment.show(APP.getSupportFragmentManager(), "timePicker");
                        return true;
                    }

                    @Override
                    public boolean onDoubleTouch() {
                        ((TextView) startViews.get(2)).setText(DateUtils.getCurrentTimeString());
                        return true;
                    }

                    @Override
                    public void onLongTouch() {
                        ((TextView) startViews.get(2)).setText(DateUtils.getCurrentTimeString());
                    }
                });

                // END-TIME
                endViews.get(0).setOnTouchListener(new OnSwipeTouchListener() {
                    @Override
                    public boolean onTouch() {
                        DialogFragment fragment = new TimePickerFragment((TextView) endViews.get(2));
                        fragment.show(APP.getSupportFragmentManager(), "timePicker");
                        return true;
                    }

                    @Override
                    public boolean onDoubleTouch() {
                        ((TextView) endViews.get(2)).setText(DateUtils.getCurrentTimeString());
                        return true;
                    }

                    @Override
                    public void onLongTouch() {
                        ((TextView) endViews.get(2)).setText(DateUtils.getCurrentTimeString());
                    }
                });
            }
        }.start();
    }
}
