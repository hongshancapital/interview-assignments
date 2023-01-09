//
//  Network.swift
//  Demo
//
//  Created by lajsf on 2022/10/31.
//

import Foundation

var source1: [DataModel] = {
    let icon = "https://cdn.yryz2.com/yangshan/html/mine/male-v1.0.4.png?x-oss-process=image/resize,m_lfit,h_0,w_900,image/auto-orient,1/quality,q_100/format,webp"
    var temp: [DataModel] = []
    for i in 1...10 {
        let data = DataModel(icon: icon, title: "第\(i)节，Google Chat is an intelligent", detail: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening", like: false)
        temp.append(data)
    }
    return temp
}()

var source2: [DataModel] = {
    let icon = "https://cdn.yryz2.com/yangshan/html/mine/male-v1.0.4.png?x-oss-process=image/resize,m_lfit,h_0,w_900,image/auto-orient,1/quality,q_100/format,webp"
    var temp: [DataModel] = []
    for i in 11...15 {
        let data = DataModel(icon: icon, title: "第\(i)节，Google Chat is an intelligent", detail: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening", like: false)
        temp.append(data)
    }
    return temp
}()
