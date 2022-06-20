//
//  UserData.swift
//  interviewiOS
//
//  Created by aa on 2022/5/4.
//

import Combine
import SwiftUI

final class UserData: ObservableObject {
    @Published var showFavoritesOnly = false
    @Published var appList = APPListInfo
}
