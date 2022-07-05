//
//  InfoItemView.swift
//  ListDemo
//
//  Created by renhe on 2022/6/24.
//

import SwiftUI

struct InfoItemView: View {
    @EnvironmentObject var viewModel : ContentViewModel
    @StateObject var infoModel : InfoModel
    var body: some View {
        HStack{
            
            AsyncCachedImage(url: URL(string: infoModel.artworkUrl100)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .cornerRadius(10)
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.secondary, lineWidth: 1)
                            .opacity(0.3)
                    }
            } placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50)
            VStack(alignment: .leading, spacing: 8) {
                Text(infoModel.trackName)
                    .font(.callout.bold())
                    .lineLimit(1)
                    .minimumScaleFactor(0.5)
                Text(infoModel.description)
                    .font(.caption.weight(.medium).leading(.tight))
                    .lineLimit(2)
            }
            Spacer(minLength: 12)
        
            Button {
                withAnimation {
                    infoModel.isLiked.toggle()
                    viewModel.updateLikeByTrackId(byTrackId: "\(infoModel.trackId)")
                }
            } label: {
                Image(systemName: infoModel.isLiked ? "heart.fill" : "heart")
                    .symbolRenderingMode(infoModel.isLiked ? .multicolor : .monochrome)
                    .foregroundColor(.secondary)
                    .scaleEffect(infoModel.isLiked ? 1.3 : 1, anchor: .center)
            }
            .buttonStyle(.plain)

        }.padding()
            .background(Color(uiColor: .systemBackground))
            .cornerRadius(10)
    }
}
