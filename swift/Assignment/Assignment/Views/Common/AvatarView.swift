//
//  AvatarView.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import SwiftUI

struct AvatarView: View {
    
    let username: String
    
    var body: some View {
        Circle()
            .fill(Color.scBackgroundAvatar)
            .overlay (
                Text(String(username.first!))
                    .foregroundColor(.white)
                    .font(.largeTitle)
            )
    }
}

struct AvatarView_Previews: PreviewProvider {
    static var previews: some View {
        AvatarView(username: "Sequoia")
    }
}
