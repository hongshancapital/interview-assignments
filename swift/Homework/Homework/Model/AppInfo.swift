//
// Homework
// AppInfo.swift
//
// Created by wuyikai on 2022/4/27.
// 

import Foundation

struct AppInfo: Identifiable {
    let id: UUID = UUID()
    let title: String
    let description: String
    let icon: String
    var isFavorite: Bool = false
}
