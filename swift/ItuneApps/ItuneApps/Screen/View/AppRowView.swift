//
//  AppRowView.swift
//  ItuneApps
//
//  Created by daicanglan on 2023/3/27.
//

import SwiftUI

struct AppRowView: View {
    @EnvironmentObject var vm: AppViewModel
    var model: AppItem
    var isLike: Bool = false
    
    var body: some View {
        HStack(alignment: .center, spacing: 0) {
            buildAvatorView(model.artworkUrl512)
            buildAppInfoView(name: model.trackName, desc: model.description)
            
            Spacer()
            
            buildLikeView()
        }

    }
}


// MARK: subviews
private extension AppRowView {
    func buildAvatorView(_ urlStr: String) -> some View {

        AsyncImage(url: URL(string: urlStr)) { image in
            image
                .resizable()
                .cornerRadius(10)
                .overlay {
                    RoundedRectangle(cornerRadius: 10)
                        .stroke(.secondary.opacity(0.7))
                }
        } placeholder: {
            ProgressView()
        }
        .frame(width: 60, height: 60)
        .padding(.trailing, 10)
    }
    
    
    func buildAppInfoView(name: String, desc: String) -> some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(name)
                .font(.system(size: 20).bold())

            Text(desc)
                .font(.system(size: 15))
                .lineLimit(2)
        }
    }
    
    func buildLikeView() -> some View {
        Image(systemName: isLike ? "heart.fill" : "heart")
            .font(.title2)
            .foregroundStyle(isLike ? .red : .gray)
            .scaleEffect(isLike ? 1.3 : 1)
            .animation(.easeInOut, value: isLike)
            .onTapGesture {
                vm.toggleLike(trackId: model.trackId)
            }
    }
}

struct AppRowView_Previews: PreviewProvider {
    static var previews: some View {
        AppRowView(model: AppItem.examples[0], isLike: true)
            .environmentObject(AppViewModel())
    }
}
