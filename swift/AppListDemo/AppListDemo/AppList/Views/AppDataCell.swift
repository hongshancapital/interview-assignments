//
//  AppDataCell.swift
//  AppListDemo
//
//  Created by 荣恒 on 2022/2/27.
//

import Foundation
import SwiftUI

struct AppDataCell: View {
    
    private var app: AppData
    private var appLikeAction: (AppData) -> ()
    
    var body: some View {
        GeometryReader { geo in
            HStack {
                AppImageView(url: app.imageUrl)
                VStack(alignment: .leading, spacing: 5) {
                    Text(app.title.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines))
                        .foregroundColor(Color.init("text-title", bundle: nil))
                        .fontWeight(Font.Weight.bold)
                        .font(Font.system(size: 16))
                    Text(app.description.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines))
                        .foregroundColor(Color.init("text-description", bundle: nil))
                        .font(Font.system(size: 12))
                        .lineLimit(2)
                }
                Spacer()
                Image.init(app.like ? "heart-full" : "heart", bundle: nil)
                    .scaleEffect(app.like ? 1.2 : 1.0)
                    .opacity(app.like ? 1.0 : 0.5)
                    .onTapGesture {
                        self.appLikeAction(app)
                    }
            }
            .frame(height: 80)
            .padding(EdgeInsets.init(top: 0, leading: 10, bottom: 0, trailing: 10))
            .background(Color.white)
            .cornerRadius(8)
        }
        .frame(height: 90)
    }
    
    init(data: AppData, action: @escaping (AppData) -> Void) {
        app = data
        appLikeAction = action
    }
}
