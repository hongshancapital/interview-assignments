//
//  STLogView.swift
//  STBaseProject
//
//  Created by stack on 2018/3/14.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit

public protocol STLogViewDelegate: NSObjectProtocol {
    func logViewBackBtnClick() -> Void
    func logViewShowDocumentInteractionController() -> Void
}

open class STLogView: UIView {
    
    private var outputPath: String = ""
    open weak var mDelegate: STLogViewDelegate?
    private var dataSources: [String] = [String]()
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    public override init(frame: CGRect) {
        super.init(frame: frame)
        self.configUI()
        self.outputPath = STConstants.st_outputLogPath()
        NotificationCenter.default.addObserver(self, selector: #selector(beginQueryLogP(notification:)), name: NSNotification.Name(rawValue: STConstants.st_notificationQueryLogName()), object: nil)
    }
    
    required public init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    private func configUI() {
        self.addSubview(self.backBtn)
        self.addSubview(self.cleanLogBtn)
        self.addSubview(self.outputLogBtn)
        self.addSubview(self.tableView)
        self.addConstraints([
            NSLayoutConstraint.init(item: self.outputLogBtn, attribute: .bottom, relatedBy: .equal, toItem: self, attribute: .bottom, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.outputLogBtn, attribute: .centerX, relatedBy: .equal, toItem: self, attribute: .centerX, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.outputLogBtn, attribute: .width, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: 100),
            NSLayoutConstraint.init(item: self.outputLogBtn, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: 80)
        ])
        self.addConstraints([
            NSLayoutConstraint.init(item: self.backBtn, attribute: .left, relatedBy: .equal, toItem: self, attribute: .left, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.backBtn, attribute: .bottom, relatedBy: .equal, toItem: self, attribute: .bottom, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.backBtn, attribute: .right, relatedBy: .equal, toItem: self.outputLogBtn, attribute: .left, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.backBtn, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: 80)
        ])
        self.addConstraints([
            NSLayoutConstraint.init(item: self.cleanLogBtn, attribute: .left, relatedBy: .equal, toItem: self.outputLogBtn, attribute: .right, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.cleanLogBtn, attribute: .bottom, relatedBy: .equal, toItem: self, attribute: .bottom, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.cleanLogBtn, attribute: .right, relatedBy: .equal, toItem: self, attribute: .right, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.cleanLogBtn, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: 80)
        ])
        self.addConstraints([
            NSLayoutConstraint.init(item: self.tableView, attribute: .top, relatedBy: .equal, toItem: self, attribute: .top, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.tableView, attribute: .left, relatedBy: .equal, toItem: self, attribute: .left, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.tableView, attribute: .bottom, relatedBy: .equal, toItem: self.cleanLogBtn, attribute: .top, multiplier: 1, constant: 0),
            NSLayoutConstraint.init(item: self.tableView, attribute: .right, relatedBy: .equal, toItem: self, attribute: .right, multiplier: 1, constant: 0)
        ])
    }
    
    @objc private func beginQueryLogP(notification: Notification) {
        if let content = notification.object as? String {
            self.beginQueryLogP(content: content)
        }
    }
    
    public func beginQueryLogP(content: String) {
        if content.count > 0 {
            self.dataSources.append(content)
        } else {
            let userDefault = UserDefaults.standard
            if let origintContent = userDefault.object(forKey: self.outputPath) as? String {
                if origintContent.count > 0 {
                    self.dataSources.removeAll()
                    self.dataSources.append(origintContent)
                }
            }
        }
        if self.dataSources.count > 0 {
            self.tableView.reloadData()
            let indexPath = IndexPath.init(row: self.dataSources.count - 1, section: 0)
            self.tableView.scrollToRow(at: indexPath, at: .bottom, animated: true)
        }
    }
    
    @objc private func backBtnClick() {
        self.mDelegate?.logViewBackBtnClick()
    }
        
    @objc private func cleanLogBtnClick() {
        self.dataSources.removeAll()
        self.tableView.reloadData()
        let userDefault = UserDefaults.standard
        userDefault.removeObject(forKey: self.outputPath)
        userDefault.synchronize()
        STFileManager.removeItem(atPath: self.outputPath)
    }
    
    @objc private func outputLogBtnClick(sender: UIButton) {
        STFileManager.st_logWriteToFile()
        self.mDelegate?.logViewShowDocumentInteractionController()
    }
        
    private lazy var tableView: UITableView = {
        let view = UITableView.init(frame: .zero, style: .plain)
        view.delegate = self
        view.dataSource = self
        view.separatorStyle = .none
        view.tableFooterView = UIView()
        view.backgroundColor = UIColor.black
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()
    
    private lazy var backBtn: UIButton = {
        let btn = UIButton()
        btn.setTitle("Back", for: .normal)
        btn.setTitleColor(UIColor.orange, for: .normal)
        btn.contentHorizontalAlignment = .center
        btn.translatesAutoresizingMaskIntoConstraints = false
        btn.addTarget(self, action: #selector(backBtnClick), for: .touchUpInside)
        return btn
    }()
    
    private lazy var cleanLogBtn: UIButton = {
        let btn = UIButton()
        btn.setTitle("Clean Log", for: .normal)
        btn.setTitleColor(UIColor.orange, for: .normal)
        btn.contentHorizontalAlignment = .center
        btn.translatesAutoresizingMaskIntoConstraints = false
        btn.addTarget(self, action: #selector(cleanLogBtnClick), for: .touchUpInside)
        return btn
    }()
    
    private lazy var outputLogBtn: UIButton = {
        let btn = UIButton()
        btn.setTitle("Output Log", for: .normal)
        btn.setTitleColor(UIColor.orange, for: .normal)
        btn.contentHorizontalAlignment = .center
        btn.translatesAutoresizingMaskIntoConstraints = false
        btn.addTarget(self, action: #selector(outputLogBtnClick(sender:)), for: .touchUpInside)
        return btn
    }()
}

extension STLogView: UITableViewDelegate, UITableViewDataSource {
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.dataSources.count
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = tableView.dequeueReusableCell(withIdentifier: "STLogViewController")
        if cell == nil {
            cell = UITableViewCell.init(style: .default, reuseIdentifier: "STLogViewController")
            cell?.selectionStyle = .none
            cell?.textLabel?.numberOfLines = 0
            cell?.backgroundColor = UIColor.black
            cell?.textLabel?.textColor = UIColor.green
        }
        let text = self.dataSources[indexPath.row]
        cell?.textLabel?.text = text
        return cell ?? UITableViewCell()
    }
    
    public func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.automaticDimension
    }
}
