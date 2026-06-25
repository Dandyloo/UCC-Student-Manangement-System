package com.ucc.studentlifemanager.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * A simple month-view calendar widget.
 * Displays the current month with today's date highlighted.
 */
public class CalenderWidget {

    public static VBox build() {
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);

        Label monthLabel = new Label(
                currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + currentMonth.getYear()
        );
        monthLabel.getStyleClass().add("calendar-month-label");

        GridPane grid = new GridPane();
        grid.setHgap(6);
        grid.setVgap(6);
        grid.setAlignment(Pos.CENTER);

        // Day-of-week headers (Sun-Sat)
        String[] dayHeaders = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (int i = 0; i < 7; i++) {
            Label header = new Label(dayHeaders[i]);
            header.getStyleClass().add("calendar-day-header");
            header.setMinWidth(32);
            header.setAlignment(Pos.CENTER);
            grid.add(header, i, 0);
        }

        // Figure out which column the 1st of the month falls on
        LocalDate firstOfMonth = currentMonth.atDay(1);
        int firstDayColumn = firstOfMonth.getDayOfWeek().getValue() % 7; // Sunday = 0

        int daysInMonth = currentMonth.lengthOfMonth();
        int row = 1;
        int col = firstDayColumn;

        for (int day = 1; day <= daysInMonth; day++) {
            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setMinWidth(32);
            dayLabel.setMinHeight(32);
            dayLabel.setAlignment(Pos.CENTER);

            if (day == today.getDayOfMonth()) {
                dayLabel.getStyleClass().add("calendar-day-today");
            } else {
                dayLabel.getStyleClass().add("calendar-day");
            }

            grid.add(dayLabel, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }

        VBox layout = new VBox(12, monthLabel, grid);
        layout.getStyleClass().add("calendar-widget");
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(18));

        return layout;
    }
}