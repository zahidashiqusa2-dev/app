# Urooj Choudhary - WhatsApp Automation Suite (Android Native)

A native Android application built with **Kotlin** and **Jetpack Compose**, implementing the full WhatsApp Automation Suite featuring direct device pairing, native Live Location GPS telemetry, synthetic Quoted Message generation, scheduled message queues, interactive button messaging, and AI-powered auto-reply rules.

## Core Features

- **Device Pairing & Local Sandbox**: Direct 8-digit phone pairing code generator (`XXXX-XXXX`) and QR code scanning with 100% device-isolated credential sandbox.
- **Native Live GPS Telemetry**: Real-time position broadcasting with radar sweep canvas animation, custom duration controls (15m, 1h, 8h), and live telemetry stats.
- **Fake Quoted Context Studio**: WhatsApp-style synthetic quoted bubble generator with vertical accent indicators, sender impersonation, and real-time chat bubble previews.
- **Scheduled Message Queue**: Background-ready dispatch scheduler with pending, sent, and cancelled state tracking and immediate dispatch triggers.
- **Interactive Button Messenger**: Rich interactive quick-reply and call-to-action button message builder with native WhatsApp card previews.
- **Automation & AI Rules Engine**: Keyword triggers (exact, contains, regex), presence simulation (auto-read, auto-type, auto-record), and Gemini AI chatbot integration.

## Architecture

- **UI Framework**: Jetpack Compose (Material Design 3)
- **Language**: Kotlin 2.2+ with AGP 9.1+ built-in Kotlin support
- **State Management**: Android Architecture Components ViewModel with Kotlin Coroutines `StateFlow`
- **Design System**: WhatsApp Dark Green (`#00A884`) and Luxury Amber Gold (`#F59E0B`) palette with custom adaptive launcher icon
