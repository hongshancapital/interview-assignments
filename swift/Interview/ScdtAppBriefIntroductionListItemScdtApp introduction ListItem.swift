//
//  ScdtListItem.swift
//  Interview
//
//  Created by 梁宇峰 on 2023/2/17.
//

import Foundation
import SwiftUI

private let APP_ICON_SIZE: CGFloat = 50
private let VIEW_HORIZONAL_PADDING: CGFloat = 20
private let LIKE_CHECK_BOX_SIZE: CGFloat = 40

struct ScdtAppBriefIntroductionListItem: View {
    @EnvironmentObject var introduction: ScdtAppIntroduction
    
    var body: some View {
        HStack {
            if let image = introduction.iconImage {
                Image(uiImage: image)
                    .resizable()
                    .frame(width: APP_ICON_SIZE, height: APP_ICON_SIZE)
                    .cornerRadius(10)
            } else {
                ProgressView().frame(width: PROCESS_VIEW_SIZE, height: PROCESS_VIEW_SIZE)
            }
            VStack(alignment: .leading) {
                Text(introduction.name)
                    .lineLimit(2)
                    .font(.subheadline.bold())
                    .alignmentGuide(.leading) { d in
                    d[.leading]
                }.padding(EdgeInsets(top: 0, leading: 0, bottom: 1, trailing: 0))
                Text(introduction.introduction)
                    .lineLimit(2)
                    .font(.caption)
                    .alignmentGuide(.leading) { d in
                    d[.leading]
                }
            }
            Spacer()
            ScdtAppBriefIntroductionLikeCheckBox(like: introduction.like).layoutPriority(1)
        }.frame(height: 80)
        .padding(EdgeInsets(top: 0, leading: VIEW_HORIZONAL_PADDING, bottom: 0, trailing: VIEW_HORIZONAL_PADDING))
        .background(Color.white)
        .cornerRadius(10)
    }
}

private struct ScdtAppBriefIntroductionLikeCheckBox: View {
    @State var like: Bool
    @State private var onClick = false
    
    var body: some View {
        ZStack {
            if like {
                let image = Image(systemName: "heart.fill")
                    .renderingMode(.template)
                    .foregroundColor(.red)
                    .scaleEffect(onClick ? 0.8 : 1.0)
                    .onAppear {
                        if onClick {
                            let animation = Animation.easeInOut(duration: 0.5)
                            withAnimation(animation) {
                                onClick = false
                            }
                        }
                    }
                image
            } else {
                Image(systemName: "heart").renderingMode(.template).foregroundColor(.gray)
            }
        }.frame(width: LIKE_CHECK_BOX_SIZE, height: LIKE_CHECK_BOX_SIZE).onTapGesture {
            onClick = true
            like.toggle()
        }
    }
}


