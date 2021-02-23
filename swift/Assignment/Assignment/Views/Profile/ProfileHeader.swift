//
//  ProfileHeader.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import SwiftUI

struct ProfileHeader: View {
    
    var user: User
    
    var body: some View {
        VStack {
            VStack(spacing: 15) {
                AvatarView(username: user.name)
                    .frame(width: 80, height: 80)
                Text(user.name)
                    .font(.headline)
                    .foregroundColor(.white)
            }
            .padding(.top, 49)
            .padding(.bottom, 30)
        }
        .frame(maxWidth: .infinity)
        .background(RoundedCorners(bl: 30, br: 30).fill(Color.scAccent))
        
    }
}

struct ProfileHeader_Previews: PreviewProvider {
    static var previews: some View {
        ProfileHeader(user: User.default)
    }
}
