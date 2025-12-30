[![CI](https://github.com/kenzo101-codeit/hack-client_for-1.21.1-neoforge/actions/workflows/ci.yml/badge.svg)](https://github.com/kenzo101-codeit/hack-client_for-1.21.1-neoforge/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://choosealicense.com/licenses/mit/)


# Hack Client for 1.21.1 (NeoForge)

A Wurst-like hack client for Minecraft 1.21.1 with a NeoForge target.

## Quick start

- Build and copy the artifact into the NeoForge run mods folder:
  - ./scripts/build-install-neoforge.sh
- Run NeoForge client for development:
  - ./gradlew :neoforge:runClient --debug

## Development / Quick-start

If you want to contribute or run the project locally, see `CONTRIBUTING.md` for a full guide. Quick commands:

- Build: `./gradlew build --no-daemon`
- Build & install for NeoForge: `./scripts/build-install-neoforge.sh`
- Run NeoForge client for testing: `./gradlew :neoforge:runClient --debug`

## Features

- Configurable click-UI keybinds (default: Right Ctrl)
- Ported modules: AutoAttack, SpeedHack (configurable multiplier), MobVision, FullBright

## Contributing

Contributions welcome — please see `CONTRIBUTING.md` for contribution guidelines. If you prefer direct contact, email **kenzo101studios@gmail.com**.

## License

MIT — see `LICENSE` for details.

### PS: Dont mind some directories or files be mentioning Wurst Client.  it was because the original maybe COPYRIGHTED project was to port Wurst Client to neoforge. but i decided to create my own hack client with VS CODE!
