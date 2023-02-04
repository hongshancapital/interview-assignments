//
//  BaseRow.swift
//  Demo
//
//  Created by hbc on 2022/2/19.
//  Copyright © 2022 hbc. All rights reserved.
//

import Foundation
import SwiftUI
import SDWebImageSwiftUI


struct BaseRow: View {
    let appInfo: AppInfo
    
    @State private var isOk = false
    
    var body: some View {
        VStack {
            HStack {
                    Spacer()
                    WebImage(url: URL(string: appInfo.imageUrl))
                        .placeholder{ProgressView()}
                        .resizable()
                        .onFailure(perform: { _ in
                            print("Failure")
                        })
                        .scaledToFit()
                        .frame(width: 55, height: 55)
                        .clipped()
                        .cornerRadius(10)
                    VStack(alignment: .leading) {
                        Text(appInfo.name)
                        .foregroundColor(.primary)
                        .font(.system(size: 16))
                        Spacer()
                        Text(appInfo.description)
                        .foregroundColor(.secondary)
                        .font(.system(size: 12))
                        .lineLimit(2)
                    }.padding(EdgeInsets(top: 10, leading: 0, bottom: 10, trailing: 0))
                    Spacer()
                    VStack(alignment:.trailing) {
                        Button(action: {
                            self.isOk = !self.isOk
                        })
                        {
                            if self.isOk {
                                Image(systemName: "heart.fill")
                                    .foregroundColor(Color.red)
                            }else{
                                Image(systemName: "heart")
                                    .foregroundColor(Color.gray)
                            }
                        }
                        .padding(10)
                        .buttonStyle(BorderlessButtonStyle())
                    }
                }
                .background(Color.white)
                .frame(width: UIScreen.main.bounds.size.width - 30)
                .cornerRadius(15)
        }
    }
}



struct BaseRow_Previews: PreviewProvider {
    static var previews: some View {
        BaseRow(appInfo: AppInfo(id: UUID(), imageUrl: "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/45/88/33/458833f5-d57e-53e1-af6b-8b6add48e5d1/source/60x60bb.jpg", name: "Social Networking", description: "1111111111116666666666"))
    }
}
