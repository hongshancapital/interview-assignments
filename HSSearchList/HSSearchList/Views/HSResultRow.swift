//
//  HSResultRow.swift
//  HSSearchList
//
//  Created by dongxia zhu on 2021/10/8.
//

import SwiftUI

struct HSResultRow: View {
    var item: HSResultItemModel
    
    func getColor() -> Color {
        if item.isInStock {
            return Color(red: 130.0 / 255, green: 163.0 / 255, blue: 241.0 / 255)
        }
        return Color(red: 184.0 / 255, green: 184.0 / 255, blue: 184.0 / 255)
    }
    
    func getBackColor() -> Color {
        if item.isInStock {
            return Color(red: 245.0 / 255, green: 248.0 / 255, blue: 254.0 / 255)
        }
        return Color(red: 246.0 / 255, green: 246.0 / 255, blue: 246.0 / 255)
    }
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Spacer()
                Text(item.name)
                    .font(.system(size: 18))
                    .fontWeight(.medium)
                    .multilineTextAlignment(.leading)
                Spacer()
                Text(item.desc)
                    .font(.system(size: 15))
                    .foregroundColor(Color.secondary)
                Spacer()
            }
            Spacer()
            HStack {
                Text(item.price)
                    .font(.system(size: 15))
                    .foregroundColor(getColor())
            }
            .padding(.vertical, 5)
            .padding(.horizontal, 13)
            .background(getBackColor())
            .cornerRadius(20)
        }
    }
}
