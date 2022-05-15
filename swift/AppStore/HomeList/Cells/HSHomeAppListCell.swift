//
//  HSHomeAppListCell.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import SwiftUI

struct HSHomeAppListCell: View {
    @ObservedObject var appInfo: HSAppInfo
    var body: some View {
        HStack {
            VStack {
                HSImageView(url: appInfo.artworkUrl60)
                    .placeholder {
                        HSLoadingView()
                    }
                    .resizable()
                    .frame(width: 60, height: 60, alignment: .center)
                    .aspectRatio(contentMode: .fit)
                    .overlay(RoundedRectangle(cornerRadius: 5).stroke(Color.border, lineWidth: 1))
                    .clipShape(RoundedRectangle(cornerRadius: 5))
            }
            VStack {
                HStack {
                    Text(appInfo.trackName)
                        .font(.system(size: 16))
                        .foregroundColor(.black)
                        .lineLimit(1)
                        .multilineTextAlignment(.leading)
                    Spacer()
                }
                Spacer(minLength: 2)
                HStack {
                    Text(appInfo.infoDescription)
                        .font(.system(size: 12))
                        .foregroundColor(.gray)
                        .lineLimit(2)
                    Spacer()
                }
            }
            VStack {
                Button {
                    appInfo.favorate.toggle()
                } label: {
                    let imageIcon = appInfo.favorate ? "favorate_selected" : "favorate"
                    Image(imageIcon)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 20, height: 20, alignment: .center)
                        .scaleEffect(appInfo.favorate ? 1.2 : 1.0)
                        .animation(Animation.easeInOut(duration: 0.2), value: appInfo.favorate)
                }
                .buttonStyle(.plain)
                .accessibilityIdentifier("favorate")

            }.frame(width: 25, height: 25, alignment: .center)
        }
        .padding(EdgeInsets(top: 10, leading: 12, bottom: 10, trailing: 12))
        .background(.white)
        .clipShape(RoundedRectangle(cornerRadius: 10))
        .listRowBackground(HSClearBackground())
    }
}

struct HSHomeAppListCell_Previews: PreviewProvider {
    static var previews: some View {
        HSHomeAppListCell(appInfo: appInfoData[0])
    }
}

struct HSClearBackground: View {
    var body: some View {
        GeometryReader { _ in
        }
    }
}
