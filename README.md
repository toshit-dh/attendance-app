# Attendo <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/img.png" height="40px"/> - Attendance Tracking App

**Attendo** is an Android app designed to help college students track their attendance effortlessly. It allows users to mark, edit attendance, add notes, manage events, and track their academic performance.

## Features

- **Mark Attendance:** Quickly mark attendance for your classes.
- **Edit Attendance:** Edit previous attendance records.
- **Add Notes:** Attach notes for any class or attendance.
- **Manage Events:** Create events for a specific day and get notified.
- **Detailed Analysis:** Provides analysis of attendance for each subject.
- **CSV Export:** Export attendance data as a CSV file for record-keeping.
- **Widget:** A home screen widget displays attendance for all subjects for quick access.
- **Notifications:** Receive notifications for events and added notes.

## Libraries Used

- **Jetpack Compose:** For building modern, responsive UIs.
- **Glance:** For creating home screen widgets.
- **Room Database:** For storing attendance data locally.
- **DataStore:** For storing app settings and user preferences.
- **WorkManager:** For scheduling background tasks such as notifications for events and notes.

## Installation

To try out the app, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/toshit-dh/attendance-app.git
   ```
2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Build and run the app on an emulator or a physical device.

## Usage

1. **Mark Attendance:** Tap on the class name to mark attendance for the day.
2. **Edit Attendance:** Long press on any attendance record to edit or delete it.
3. **Add Notes:** Add notes for each class in the corresponding section.
4. **Manage Events:** Create events by selecting the desired date and adding event details.
5. **View Analysis:** Check your attendance analysis in the 'Analysis' section to see subject-wise attendance.
6. **Widget:** Add the widget to your home screen for quick access to attendance summary.

## Screenshots
<table>
  <tr>
    <th>Light Mode</th>
    <th>Dark Mode</th>
  </tr>
  <tr>
    <td style="flex: 1;">
      <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/light_today_attendance.png" alt="Light Today Attendance" width="100%" height="400px">
    </td>
    <td style="flex: 1;">
      <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/dark_today_attendance.png" alt="Dark Today Attendance" width="100%" height="400px">
    </td>
  </tr>
  <tr>
    <td style="flex: 1;">
      <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/light_analysis.png" alt="Light Analysis" width="100%" height="400px">
    </td>
    <td style="flex: 1;">
      <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/dark_analysis.png" alt="Dark Analysis" width="100%" height="400px">
    </td>
  </tr>
  <tr>
    <td style="flex: 1;">
      <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/light_notes.png" alt="Light Notes" width="100%" height="400px">
    </td>
    <td style="flex: 1;">
      <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/dark_notes.png" alt="Dark Notes" width="100%" height="400px">
    </td>
  </tr>
  <tr>
    <td style="flex: 1;">
      <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/light_events.png" alt="Light Events" width="100%" height="400px">
    </td>
    <td style="flex: 1;">
      <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/dark_events.png" alt="Dark Events" width="100%" height="400px">
    </td>
  </tr>
  <tr>
    <td style="flex: 1;">
      <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/light_edit_attendance.png" alt="Light Edit Attendance" width="100%" height="400px">
    </td>
    <td style="flex: 1;">
      <img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/dark_edit_attendance.png" alt="Dark Edit Attendance" width="100%" height="400px">
    </td>
  </tr>
</table>

### Widget
<img src="https://github.com/toshit-dh/attendance-app/blob/main/app/src/main/res/drawable/widget.png" alt="Dark Edit Attendance" height="400px">


## App Settings

- **Theme:** Change the theme of the app from the Settings section.
- **Notifications:** Set events notification time.
- **Mark Attendance** Mark attendance if you fail to mark for a day.
