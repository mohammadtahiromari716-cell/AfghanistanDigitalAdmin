package com.afghanistandigital.admin

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.*
import android.content.Context

class MainActivity : Activity() {
    private val green = Color.rgb(18, 60, 45)
    private val gold = Color.rgb(182, 138, 53)
    private val ink = Color.rgb(35, 45, 40)
    private val prefs by lazy { getSharedPreferences("admin_local", Context.MODE_PRIVATE) }
    private var language = "دری"
    private lateinit var root: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showPinScreen()
    }

    private fun setupRoot() {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 28, 24, 24)
            setBackgroundColor(Color.rgb(245, 247, 245))
        }
        val scroll = ScrollView(this).apply { addView(root) }
        setContentView(scroll)
    }

    private fun title(text: String) {
        root.addView(TextView(this).apply {
            this.text = text
            textSize = 25f
            setTextColor(green)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 22)
        })
    }

    private fun button(text: String, action: () -> Unit) {
        root.addView(Button(this).apply {
            this.text = text
            setTextColor(Color.WHITE)
            setBackgroundColor(green)
            isAllCaps = false
            textSize = 16f
            setOnClickListener { action() }
        }, LinearLayout.LayoutParams(-1, 54).apply { bottomMargin = 12 })
    }

    private fun showPinScreen() {
        setupRoot()
        title("AFGHANISTAN DIGITAL\nADMIN")
        root.addView(TextView(this).apply {
            text = "ورود مدیر  |  Admin Login"
            textSize = 18f
            setTextColor(ink)
            gravity = Gravity.CENTER
            setPadding(0, 8, 0, 18)
        })
        val pin = EditText(this).apply {
            hint = "رمز چهاررقمی / 4-digit PIN"
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            textSize = 18f
            gravity = Gravity.CENTER
        }
        root.addView(pin, LinearLayout.LayoutParams(-1, 56))
        button("ورود / Login") {
            val saved = prefs.getString("pin", "1234") ?: "1234"
            if (pin.text.toString() == saved) showDashboard()
            else Toast.makeText(this, "رمز نادرست است / Incorrect PIN", Toast.LENGTH_LONG).show()
        }
        root.addView(TextView(this).apply {
            text = "رمز اولیه: 1234 — پس از ورود آن را تغییر دهید.\nInitial PIN: 1234 — change it after first login."
            textSize = 13f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 20)
        })
    }

    private fun showDashboard() {
        setupRoot()
        title("افغانستان دیجیتال\nمدیریت")
        root.addView(TextView(this).apply {
            text = "پنل مدیریت  |  Admin Dashboard"
            textSize = 17f
            setTextColor(ink)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 18)
        })
        button("مدیریت مشتریان / Manage Customers") { showInfo("مدیریت مشتریان", "این بخش در نسخه نمایشی است. برای حساب‌های واقعی باید سرور امن وصل شود.") }
        button("کاربران فعال / Active Users") { showInfo("کاربران فعال", "فهرست کاربران پس از اتصال پایگاه داده آنلاین نمایش داده می‌شود.") }
        button("اشتراک‌ها / Subscriptions") { showInfo("اشتراک‌ها", "مدیریت تاریخ انقضا و تمدید پس از راه‌اندازی سرور فعال می‌شود.") }
        button("گزارش فعالیت / Activity Logs") { showInfo("گزارش فعالیت", "گزارش‌ها در نسخه آنلاین ثبت خواهند شد.") }
        button("تغییر رمز مدیر / Change PIN") { showChangePin() }
        button("زبان / Language: $language") {
            language = if (language == "دری") "English" else if (language == "English") "پشتو" else "دری"
            showDashboard()
        }
        button("قفل برنامه / Lock") { showPinScreen() }
        root.addView(TextView(this).apply {
            text = "نسخه آغازین رابط کاربری — اطلاعات مشتریان هنوز به سرور آنلاین وصل نیست.\nStarter UI — customer records are not yet connected to an online server."
            textSize = 12f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(8, 20, 8, 8)
        })
    }

    private fun showChangePin() {
        setupRoot()
        title("تغییر رمز مدیر")
        val oldPin = EditText(this).apply { hint = "رمز فعلی / Current PIN"; inputType = 2 }
        val newPin = EditText(this).apply { hint = "رمز جدید چهاررقمی / New 4-digit PIN"; inputType = 2 }
        root.addView(oldPin)
        root.addView(newPin)
        button("ذخیره / Save") {
            val current = prefs.getString("pin", "1234") ?: "1234"
            val next = newPin.text.toString()
            if (oldPin.text.toString() != current) {
                Toast.makeText(this, "رمز فعلی نادرست است", Toast.LENGTH_SHORT).show()
            } else if (!next.matches(Regex("\\d{4}"))) {
                Toast.makeText(this, "رمز جدید باید دقیقاً ۴ رقم باشد", Toast.LENGTH_SHORT).show()
            } else {
                prefs.edit().putString("pin", next).apply()
                Toast.makeText(this, "رمز تغییر کرد", Toast.LENGTH_SHORT).show()
                showDashboard()
            }
        }
        button("بازگشت / Back") { showDashboard() }
    }

    private fun showInfo(heading: String, message: String) {
        setupRoot()
        title(heading)
        root.addView(TextView(this).apply {
            text = message
            textSize = 16f
            setTextColor(ink)
            gravity = Gravity.CENTER
            setPadding(8, 20, 8, 28)
        })
        button("بازگشت / Back") { showDashboard() }
    }
}
