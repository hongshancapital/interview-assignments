//
//  AppButton.swift
//  AppListDemo
//
//  Created by Mars on 2023/2/4.
//

import SwiftUI

struct AppButton: View {
    @StateObject var app:DataItem
    var body: some View {
        Button{
            app.isLove = !app.isLove
        } label: {
            if app.isLove == true {
                Image(systemName: "heart.fill")
            } else {
                Image(systemName: "heart")
            }
        }
        .foregroundColor(.red)
        .padding()
        .frame(width: 30,height: 30)
    }
}

struct AppButton_Previews: PreviewProvider {
    static var previews: some View {
        AppButton(app: appData[0])
    }
}
