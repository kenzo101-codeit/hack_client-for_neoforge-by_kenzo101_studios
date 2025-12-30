Thank you for your interest in contributing to Hack Client for (NeoForge)! ✅

This guide explains how the project is organized, how to set up a development environment, and what we expect from contributions.

1) Project goals and scope
- Purpose: provide a Wurst-like mod with a configurable click UI, keybinds, and a small set of ported modules (AutoAttack, SpeedHack, MobVision, FullBright). The long-term plan is to expand modules, improve cross-runtime compatibility, and prepare beta releases on Modrinth.
- Scope: short-term priority is to make the NeoForge runtime stable and test modules in singleplayer; later work will add multiplayer-friendly (client-side) implementations and automated publishing.

2) What we are working on now
- Core: KeybindManager, GUI (ModuleScreen), and per-tick integration (ClientTickMixin).
- Modules: AutoAttack, SpeedHack (configurable multiplier), MobVision, FullBright. Fly is planned but not implemented yet.
- Packaging & CI: scripts to build + copy jars (scripts/), and a GitHub Actions workflow that runs `./gradlew build` and uploads artifacts.

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

5) Coding style and best practices
- Keep changes small and focused.
- Follow existing patterns in the `common` module for feature classes and `neoforge` module for client-specific code (GUI, mixins).
- Avoid adding or committing local runtime artifacts (see `.gitignore` for details). If you must include generated resources, describe why in the PR.

6) Pull request checklist
- Create a clear PR title and description explaining the change and motivation.
- Include a short testing / verification section describing how you tested the change (manual steps for runClient or automated tests).
- Run `./gradlew build` and make sure the project compiles.
- If the change affects public APIs or adds a new module, add documentation to the README or a dedicated docs file.

7) Security & Code of Conduct
- Sensitive issues (security, personal data): open the issue privately via email to **kenzo101studios@gmail.com** and mark it `private` in the subject, or contact a maintainer.
- We follow a simple code of conduct: be respectful, provide constructive feedback, and avoid abusive or harassing language. PR reviewers may request changes; be responsive and cooperative.

8) Contact & support
- For quick questions or contribution coordination: **kenzo101studios@gmail.com**
- Prefer GitHub issues for public problems and PRs for code contributions.

Thank you — contributions and feedback are welcome! 🙏
