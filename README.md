# InfiniteGame

InfiniteGame is a minecraft plugin able to regenerate map from a backup on server start.

## About

That extension work on Linux and Windows with a provided start script.

## Installation

This plugin has been devloped for PaperMC 1.14.4, it require a `infinite.json` in the root folder.

```json
{
  "worldFolder": "world",
  "worldBackup": "world_backup",
  "windowsScript": "./start.bat",
  "linuxScript": "./start.sh"
}
```

## Usage

Simply put the jar file into the plugin folder and make a file `infinite.json` in server root directory

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[GPLv3](https://choosealicense.com/licenses/gpl-3.0/), see `LICENSE`