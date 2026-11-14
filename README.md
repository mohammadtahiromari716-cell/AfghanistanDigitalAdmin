# Afghanistan Digital Admin

A starter Android project for the Afghanistan Digital Admin app.

## Included
- Kotlin Android application
- Admin PIN screen (initial local PIN: `1234`)
- Changeable four-digit local PIN
- Starter admin dashboard UI
- GitHub Actions workflow to build a debug APK

## Important security notice
This is a **starter UI prototype**, not a production-ready online administration system. Customer accounts, subscriptions, activity logs, and permissions are placeholders. Do not use it to store real customer or financial data yet.

A secure backend (for example, Firebase or Supabase) must still be configured. Authentication and authorization must be enforced by the backend, passwords must never be stored as plain text, and private server keys must never be committed to this public repository. The local PIN is only a basic on-device screen lock and is not a replacement for secure administrator authentication.

## Build
Open this folder in Android Studio and let Gradle sync. Or push it to GitHub and use the included Actions workflow to build a debug APK.
