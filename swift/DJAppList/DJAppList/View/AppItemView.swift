//
//  AppItemView.swift
//  AppList
//
//  Created by haojiajia on 2022/7/7.
//

import SwiftUI
import Combine
import SDWebImageSwiftUI

struct AppItemView: View {
    
    @EnvironmentObject var store: AppStore
    
    let item: AppItem
    
    var body: some View {
        HStack {
            // imageView
            WebImage(url: URL(string: item.image))
                .placeholder {
                   ProgressView()
                }
                .resizable()
                .aspectRatio(contentMode: .fill)
                .overlay {
                    RoundedRectangle(cornerRadius: 16, style: .continuous)
                        .stroke(.gray, lineWidth: 0.5)
                }
                .frame(width: 64, height: 64, alignment: .center)
                .cornerRadius(16)
            
            // Text
            VStack(alignment: .leading, spacing: 6) {
                Text(item.name)
                    .lineLimit(1)
                    .font(.headline)
                Text(item.description)
                    .lineLimit(2)
                    .font(.caption)
            }
            
            // spacer
            Spacer()
            
            // likeButton
            Button {
                self.store.dispatch(.toggleLike(id: item.id))
            } label: {
                Image(systemName: item.isLike ? "heart.fill" : "heart")
                    .foregroundColor(item.isLike ? .red : .gray)
                    .scaleEffect(item.isLike ? 1.2 : 1.0)
            }
            .frame(width: 44, height: 44, alignment: .trailing)
            .animation(.interpolatingSpring(stiffness: 50, damping: 5), value: item.isLike)
        }
        .padding(EdgeInsets(top: 16, leading: 16, bottom: 16, trailing: 16))
        .background(Color(UIColor.systemBackground))
        .cornerRadius(16)
    }
}

#if DEBUG
struct AppItemView_Preview: PreviewProvider {
    static var previews: some View {
        SampleItemView()
        SampleItemView().environment(\.colorScheme, .dark)
        SampleItemView().previewDevice("iPhone SE")
    }
}

struct SampleItemView: View {
    var body: some View {
        VStack {
            AppItemView(item: AppStore.sample(index: 0))
                .environmentObject(AppStore())
            Spacer()
        }
        .background(Color(UIColor.secondarySystemBackground))
    }
}
#endif
