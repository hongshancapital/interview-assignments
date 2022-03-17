//
//  AppItemView.swift
//  ListViewDemo
//
//  Created by HL on 2022/3/15.
//

import SwiftUI
import Kingfisher

struct LVAppRow: View {
    
    @ObservedObject var app: LVApp = LVApp()
    
    var body: some View {
        ZStack {
            HStack {
                KFImage(URL(string: app.artworkUrl512 ?? ""))
                    .placeholder({
                        ImagePlaceholderView()
                    })
                    .resizable()
                    .cornerRadius(8)
                    .frame(width: 60, height: 60)
                    .padding(EdgeInsets(top: 16, leading: 16, bottom: 16, trailing: 8))
                VStack(alignment: .leading) {
                    Text(app.trackCensoredName ?? "")
                        .foregroundColor(.black)
                        .font(.headline)
                        .lineLimit(1)
                    Text(app.releaseNotes ?? "")
                        .foregroundColor(.black)
                        .font(.caption)
                        .lineLimit(2)
                }
                Spacer(minLength: 16)
//                Image(systemName: app.selected ? "heart.fill" : "heart")
//                    .foregroundColor(app.selected ? .red : .black)
//                    .onTapGesture {
//                        app.selected.toggle()
//                    }
                Button {
                    app.selected.toggle()
                } label: {
                    Image(systemName: app.selected ? "heart.fill" : "heart")
                        .foregroundColor(app.selected ? .red : .black)
                }

            }
            .padding(.trailing, 16)
        }
        .background(.white)
        .padding(EdgeInsets(top: 0, leading: 12, bottom: 0, trailing: 12))
    }
}
