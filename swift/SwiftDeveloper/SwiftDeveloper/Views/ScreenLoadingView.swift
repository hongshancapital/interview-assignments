//
//  ScreenLoadingView.swift
//  SwiftDeveloper
//
//  Created by zhe wu on 2022/10/19.
//

import SwiftUI

struct ScreenLoadingView: View {
    var body: some View {
        VStack {
            Spacer()

            HStack {
                Spacer()
                ProgressView()
                Spacer()
            }
            
            Spacer()
        }
        .background(Color(UIColor.systemGroupedBackground))
    }
}
