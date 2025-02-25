//
//  PostCell.swift
//  swiftDemo
//
//  Created by chenlp on 2022/4/11.
//

import SwiftUI
import SDWebImageSwiftUI

struct PostCell: View {
    
    let post: Post
    var bindingPost: Post {
        userData.post(forId: post.artistId)!
    }    
    @EnvironmentObject var userData: UserData
    
    var data : Doc
    var isLast :Bool
    @ObservedObject  var  listData: getData
    @Binding var isLastOver: Bool

    var body: some View {
        var post = bindingPost
        VStack() {
            HStack() {
                WebImage(url: URL(string: post.artworkUrl60))
                    .resizable()
                    .scaledToFill()
                    .frame(width: 50, height: 50)
                    .cornerRadius(13)
                
                VStack(alignment: .leading, spacing: 5) {
                    if self.isLast {
                        Text(data.article_type).font(.caption)
                            .font(.system(size: 22))
                            .fontWeight(.bold)
                            .lineLimit(1)
                            .onAppear {
                                if self.listData.data.count != 50{
                                    self.listData.updateData()
                                    print("load data...")
                                }else{
                                    self.isLastOver = true
                                    print("no more data!!!")
                                }
                                
                            }
                    }else{
                        Text(data.article_type).font(.caption)
                            .font(.system(size: 22))
                            .fontWeight(.bold)
                            .lineLimit(1)
                    }

                    
                    Text(post.description)
                        .font(.system(size: 15))
                        .lineLimit(2)
                    Spacer()
                    
                }
                Spacer()
                PostCellToolbarButton(image: post.isVppDeviceBasedLicensingEnabled ?  "heart": "heart.fill",
                                      color: post.isVppDeviceBasedLicensingEnabled ?  .gray: .red)
                {
                    if post.isVppDeviceBasedLicensingEnabled {
                        post.isVppDeviceBasedLicensingEnabled = false
                    } else {
                        post.isVppDeviceBasedLicensingEnabled = true
                    }
                    self.userData.update(post)
                }
                
            }
        }
        .padding()
        .background(.white)
        .clipShape(RoundedRectangle(cornerRadius: 10))
        .listRowBackground(Color.init(red: 239 / 255, green: 238 / 255, blue: 245 / 255, opacity: 0.1))
        .listRowSeparator(.hidden)
        
    }
}

struct PostCell_Previews: PreviewProvider {
    static var previews: some View {
        let userData = UserData()
        let listData = getData()
        return PostCell(post: userData.recommendPostList.results[0], data: listData.data[0], isLast: true, listData: getData(), isLastOver: .constant(false)).environmentObject(userData)
    }
}
