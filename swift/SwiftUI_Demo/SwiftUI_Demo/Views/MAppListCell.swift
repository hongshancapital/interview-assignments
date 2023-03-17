//
//  MAppListCell.swift
//  SwiftUI_Demo
//
//  Created by mazb on 2022/9/2.
//

import SwiftUI

struct MAppListCell: View {
    
    @StateObject private var cellVM: MListCellViewModel
    
    var animation: Animation{
        Animation.linear
    }
    
    init(viewInfo: MViewInfo) {
        _cellVM = StateObject(wrappedValue: MListCellViewModel(viewInfo: viewInfo))
    }
    
    var body: some View {
        
        HStack {
            
            imageView
            
            VStack(alignment:.leading, spacing: 5) {
                Text(cellVM.viewInfo.trackName)
                    .lineLimit(1)
                    .font(.title2)
                
                Text(cellVM.viewInfo.description)
                    .lineLimit(2)
                    .font(.caption)
            }
            .padding(.trailing, 5)
            
            Spacer()
            
            favouriteButton
            
        }
        .padding()
        .background(Color.white)
        .cornerRadius(10)
    }
}


extension MAppListCell {
    
    private var imageView: some View {
        ZStack {
            if let image = cellVM.image {
                Image(uiImage: image)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .cornerRadius(10)
                    .background(
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(Color.gray, lineWidth: 1.0)
                            .opacity(0.5)
                    )
            } else if cellVM.isLoading {
                ProgressView()
            } else {
                Image(systemName: "questionmark")
                    .foregroundColor(.secondary)
                    .onTapGesture {
                        
                    }
            }
        }
        .frame(width: 50, height: 50)
    }
    
    private var favouriteButton: some View {
        Button {
            withAnimation {
                cellVM.viewInfo.isSelected.toggle()
            }
        } label: {
            Image(systemName: cellVM.viewInfo.isSelected ? "heart.fill" : "heart")
                .resizable()
                .scaledToFill()
        }
        .frame(width: 16, height: 16)
        .scaleEffect(cellVM.viewInfo.isSelected ? 1.2 : 1.0)
    }
}


struct MAppListCell_Previews: PreviewProvider {
    static var previews: some View {
        
        let viewInfo = MViewInfo(
            trackId: 414478124,
            trackName: "WeChat",
            artworkUrl100: "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/d9/33/2b/d9332b33-6c82-b73a-f394-8274ab88e4c0/AppIcon-0-0-1x_U007emarketing-0-0-0-4-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/100x100bb.jpg",
            description: "WeChat is more than a messaging and social media app – it is a lifestyle for one billion users across the world. Chat and make calls with friends, read news and use local services in Official Accounts and Mini Programs, play games with friends, enjoy mobile payment features with WeChat Pay, and much more.\n\nWhy do one billion people use WeChat?\n• MORE WAYS TO CHAT: Message friends using text, photo, voice, video, location sharing, and more. Create group chats with up to 500 members.\n• VOICE & VIDEO CALLS: High-quality voice and video calls to anywhere in the world. Make group video calls with up to 9 people.\n• REAL-TIME LOCATION: Not good at explaining directions? Share your real-time location with the tap of a button.\n• MOMENTS: Never forget your favorite moments. Post photos, videos, and more to share with friends on your personal Moments stream.\n• TIME CAPSULE (NEW!): Share glimpses of your day. Record short videos to post in your Time Capsule before they disappear in 24 hours.\n• STICKER GALLERY: Browse tho",
            isSelected: false
        )
        
        MAppListCell(viewInfo: viewInfo)
    }
}
