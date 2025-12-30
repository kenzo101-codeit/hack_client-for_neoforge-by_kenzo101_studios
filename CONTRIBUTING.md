Thank you for your interest in contributing to Hack Client for NeoForge! ✅

This guide explains how the project is organized, how to set up a development environment, and what we expect from contributions.

1) Project goals and scope
- Purpose: provide a Wurst-like mod with a configurable click UI, keybinds, and a small set of ported modules (AutoAttack, SpeedHack, MobVision, FullBright). The long-term plan is to expand modules, improve cross-runtime compatibility, and prepare beta releases on Modrinth.
- Scope: short-term priority is to make the NeoForge runtime stable and test modules in singleplayer; later work will add multiplayer-friendly (client-side) implementations and automated publishing.

2) What we are working on now
- Core: KeybindManager, GUI (ModuleScreen), and per-tick integration (ClientTickMixin). (Already Finished)
- Modules: Still Planning it...
- Packaging & CI: scripts to build + copy jars (scripts/), and a GitHub Actions workflow that runs `./gradlew build` and uploads artifacts.
- porting to 1.20.4 and other versions.

3) Development environment (quick setup)
- Requirements: JDK 21, Gradle wrapper (bundled), and `gh` for repository tasks (optional).
- Common commands:
	- Build: `./gradlew build --no-daemon`
	- Build and install for NeoForge: `./scripts/build-install-neoforge.sh`
	- Run NeoForge client: `./gradlew :neoforge:runClient --debug` (important: use the `:neoforge:runClient` task to run NeoForge specifically)
	- Clean: `./gradlew clean`

4) How to run tests and smoke checks
- There are currently no unit tests; manual smoke tests are important:
	- Launch NeoForge with `:neoforge:runClient`.
	- Verify the menu opens (default: Right Ctrl), binding capture persists in `config/hackclient-binds.properties`, and modules behave as documented.
