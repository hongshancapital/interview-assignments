package config

import (
	"log"

	"github.com/BurntSushi/toml"
)

type Config struct {
	LogLevel string `toml:"log_level"`
	LogPath  string `toml:"log_path"`
	LogName  string `toml:"log_name"`
}

var conf *Config = nil

func InitConfig(path string) {
	conf = &Config{}

	if _, err := toml.DecodeFile(path, conf); err != nil {
		log.Fatalf("init conf err = %v ", err)
	}
}

func GetLogLevel() string {
	return conf.LogLevel
}

func GetLogPath() string {
	return conf.LogPath
}

func GetLogName() string {
	return conf.LogName
}
