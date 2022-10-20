//
//  AppItemCellView.swift
//  Test1
//
//  Created by Stephen Li on 2022/10/18.
//

import Foundation
import SwiftUI

struct AppItemCellView: View {
    // View model to show
    @State var itemVM: AppItemViewModel
    
    let iconSize = 60.0
    
    var body: some View {
        HStack {
            // Icon image
            AsyncImage(url: URL(string: itemVM.iconImageURL)) { image in
                image.resizable().aspectRatio(contentMode: .fill)
            } placeholder: {
                ProgressView()
            }
            .cornerRadius(10)
            .frame(width: iconSize, height: iconSize, alignment: .center)
            
            // Name and description
            VStack(alignment: .leading) {
                Text(itemVM.name)
                    .font(.system(size: 18))
                    .fontWeight(.bold)
                    .lineLimit(1)
                
                Spacer()
                    .frame(height: 3)
                
                Text(itemVM.description)
                    .font(.system(size: 13))
                    .lineLimit(2)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
            
            // Favorite button
            Image(systemName: (itemVM.isFavorited ? "heart.fill" : "heart"))
                .foregroundColor((itemVM.isFavorited ? .red : .gray))
                .imageScale(.large)
                .onTapGesture {
                    itemVM.isFavorited = !itemVM.isFavorited
                }
        }
        .padding()
        .frame(maxHeight: .infinity)
        .background(.white)
        .cornerRadius(10.0)
    }
}

struct AppItemCellView_Previews: PreviewProvider {
    static var previews: some View {
        AppItemCellView(itemVM: AppItemViewModel(1234, "Google Chat", "Skype keeps the world talking. Say “hello” with an instant message, voice or video call – all for free*, no matter what device they use Skype on. Skype is available on phones, tablets, PCs, and Macs.\n\n• Video calls – Don’t just hear the cheers, see them! Get together with 1 or 49 of your", false, "https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/b7/b7/02/b7b7021e-d7bb-3674-30c0-ab6764ab2ae0/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg"))
            .frame(height: 80)
    }
}
